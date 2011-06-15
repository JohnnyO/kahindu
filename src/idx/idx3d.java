// | The idx3d engine (and this source code) is (c)1998 by Peter Walser
// | Feel free to use this 3d engine for non-commercial purpose,
// | and please set a link to my homepage.
// | If you have any questions concerning the source code the 
// | methods and algorithms: send me e-mail.
// |
// | Peter Walser  
// | proxima@active.ch
// | http://www.vis.inf.ethz.ch/~pwalser
// | -------------------------------------------------------------
package idx;

import java.applet.Applet;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.Date;

class idx3d extends Applet
{
	// Copyright information
	public String info=new String("idx3d Java 3d ENGINE");
	public String copyright=new String("©1998 by Peter Walser");
	public String version=new String("Version 2.0 BETA");
	public String build=new String("06.10.1998");
	public String sysinfo;

	//Scene attributes
	int w;
	int h;
	float centerx;
	float centery;

	//Objects and Lights
	public idx3d_object object[];
	public idx3d_light light[];
	public idx3d_texture texture[];
	public int objects=0;
	public int lights=0;	
	public int textures=0;
	public int maxobjects=0;
	public int maxlights=0;
	public int maxtextures=0;
	private idx3d_matrix worldmatrix=new idx3d_matrix();
	
	//Color management
	public int bgcolor;
	int alpha=0xff000000;
	int rbit=0x00ff0000;
	int gbit=0x0000ff00;
	int bbit=0x000000ff;
	private ColorModel idx_cm=ColorModel.getRGBdefault();

	//Trigonometry
	float sinus[]=new float[360];
	float cosinus[]=new float[360];	 
	final float pi=(float)3.14159265;
	final float deg2rad=pi/180;

	//Scene parameters
	public float perspective=(float)8;
	public float zoomcorrection=(float)0.72;
	public int ambient=0;  //0..255
	public int phong=80; //0..255
	public int reflectivity=255; //0..255
	public boolean staticLight=false;

	//Render structures
	int LightMap[][]=new int[256][256];
	int ColorMap[][]=new int[256][256];
	int EnvMap[][]=new int[256][256];
	int StaticLightMap[][]=new int[256][256];
	int StaticEnvMap[][]=new int[256][256];
	int zBuffer[][];
	int LEdgeBuffer[];
	int REdgeBuffer[];
	int ZEdgeBufferL[];
	int ZEdgeBufferR[];
	int IEdgeBufferL[];
	int IEdgeBufferR[];
	int NXEdgeBufferL[];
	int NYEdgeBufferL[];
	int NXEdgeBufferR[];
	int NYEdgeBufferR[];
	int TXEdgeBufferL[];
	int TYEdgeBufferL[];
	int TXEdgeBufferR[];
	int TYEdgeBufferR[];
	int zInfinite=1000<<16;
	int zNear=-1<<16;
	
	//Output
	Image DoubleBuffer=null;
	int TargetPixel[];
	int Background[];
		
	// GENERAL METHODS
	
		public idx3d(int width, int height)
		{
			myResize(width,height);
		}

		public void myResize(int width, int height)
		{
			w=width;
			h=height;
			centerx=(float)w/2;
			centery=(float)h/2;
			sysinfo=getSysInfo();
			TargetPixel=new int[w*h];
			Background=new int[w*h];
			LEdgeBuffer=new int[h];
			REdgeBuffer=new int[h];
			zBuffer=new int[w][h];
			ZEdgeBufferL=new int[h];
			ZEdgeBufferR=new int[h];
			IEdgeBufferL=new int[h];
			IEdgeBufferR=new int[h];
			NXEdgeBufferL=new int[h];
			NYEdgeBufferL=new int[h];
			NXEdgeBufferR=new int[h];
			NYEdgeBufferR=new int[h];
			TXEdgeBufferL=new int[h];
			TYEdgeBufferL=new int[h];
			TXEdgeBufferR=new int[h];
			TYEdgeBufferR=new int[h];
	
			bgcolor=getIntColor(0,0,0);
			clearBackground();
			init_LightMap();
			init_ColorMap();
			init_TrigTables();
		}

		private String getSysInfo()
		{
			return (System.getProperty("os.arch")+" on "+System.getProperty("os.name"));
		}

		private void init_LightMap()
		{
			float NX;
			float NY;
			float NZ;
			for (int j=0;j<256;j++)
			{
				for (int i=0;i<256;i++)
				{
					NX=((float)i-127)/127;
					NY=((float)j-127)/127;
					NZ=(float)(1-Math.sqrt(NX*NX+NY*NY));
					LightMap[i][j]=crop((int)(NZ*255),0,255);
				}
			}
		}

		public void setStatic()
		{
			for (int j=0;j<256;j++)
			{
				for (int i=0;i<256;i++)
				{
					StaticLightMap[i][j]=getIntensity(2*i-255,2*j-255);
					StaticEnvMap[i][j]=getEnvironment(2*i-255,2*j-255);
				}
			}
			staticLight=true;
		}

		private void init_ColorMap()
		{
			int H2=255-phong;
			for(int x=0;x<256;x++)
			{
				for (int y=0;y<H2;y++)
				{
					ColorMap[y][x]=(x*y/H2);
				}
				for (int y=H2;y<256;y++)
				{
					ColorMap[y][x]=(y+(255-y)*(x-H2)/phong);
				}
			}	
		}

		private void init_TrigTables()
		{
			for(int i=0;i<360;i++)
			{
				sinus[i]=(float)Math.sin(deg2rad*(float)i);
				cosinus[i]=(float)Math.cos(deg2rad*(float)i);
			}
		}

		public void setPhong(int p)
		{
			phong=p;
			init_ColorMap();
		}

		public float crop(float a, float b, float c)
		{
			if (a<b) return (b);
			if (a>c-1) return (c-1);
			return a;
		}

		public int crop(int a, int b, int c)
		{	
			if (a<b) return (b);
			if (a>c-1) return (c-1);
			return a;
		}

		public boolean inrange(int a, int b, int c)
		{
			return((a>=b)&&(a<c));
		}

		public boolean inrange(float a, float b, float c)
		{
			return((a>=b)&&(a<c));
		}

		public float rnd()
		{
			return ((float)Math.random()*2-1);
		}

		public idx3d_node rndNode()
		{
			idx3d_vector v=new idx3d_vector();
			v=rndVector();
			idx3d_node r=new idx3d_node(v.x,v.y,v.z);
			r.n=normalize(rndVector());
			return r;
		}

		public idx3d_vector rndVector()
		{
			idx3d_vector v=new idx3d_vector();
			v.x=rnd();
			v.y=rnd();
			v.z=rnd();
			return v;
		}
	
		public int rndColor()
		{
			int r=(int)(Math.random()*255);
			int g=(int)(Math.random()*255);
			int b=(int)(Math.random()*255);
			return getIntColor(r,g,b);
		}

	// GEOMETRY FUNCTIONS

		public idx3d_vector vectordist(idx3d_vector v1, idx3d_vector v2)
		{
			return new idx3d_vector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
		}

		public float vectorlength(idx3d_vector v)
		{
			return (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
		}

		public idx3d_vector normalize(idx3d_vector v)
		{
			float length=vectorlength(v);
			return new idx3d_vector(v.x/length,v.y/length,v.z/length);
		}

		public idx3d_vector getNormal(idx3d_vector a, idx3d_vector b, idx3d_vector c)
		{
			idx3d_vector n=new idx3d_vector();
			float ax=b.x-a.x;
			float ay=b.y-a.y;
			float az=b.z-a.z;
			float bx=c.x-a.x;
			float by=c.y-a.y;
			float bz=c.z-a.z;
			n.x=ay*bz-by*az;
			n.y=az*bx-bz*ax;
			n.z=ax*by-bx*ay;
			return normalize(n);		
		}

		public idx3d_matrix crossproduct(idx3d_matrix a, idx3d_matrix b)
		{
			idx3d_matrix c=new idx3d_matrix();
			c.matrix[0][0]=a.matrix[0][0]*b.matrix[0][0]+a.matrix[0][1]*b.matrix[1][0]+a.matrix[0][2]*b.matrix[2][0]+a.matrix[0][3]*b.matrix[3][0];
			c.matrix[0][1]=a.matrix[0][0]*b.matrix[0][1]+a.matrix[0][1]*b.matrix[1][1]+a.matrix[0][2]*b.matrix[2][1]+a.matrix[0][3]*b.matrix[3][1];
			c.matrix[0][2]=a.matrix[0][0]*b.matrix[0][2]+a.matrix[0][1]*b.matrix[1][2]+a.matrix[0][2]*b.matrix[2][2]+a.matrix[0][3]*b.matrix[3][2];
			c.matrix[0][3]=a.matrix[0][0]*b.matrix[0][3]+a.matrix[0][1]*b.matrix[1][3]+a.matrix[0][2]*b.matrix[2][3]+a.matrix[0][3]*b.matrix[3][3];
			c.matrix[1][0]=a.matrix[1][0]*b.matrix[0][0]+a.matrix[1][1]*b.matrix[1][0]+a.matrix[1][2]*b.matrix[2][0]+a.matrix[1][3]*b.matrix[3][0];
			c.matrix[1][1]=a.matrix[1][0]*b.matrix[0][1]+a.matrix[1][1]*b.matrix[1][1]+a.matrix[1][2]*b.matrix[2][1]+a.matrix[1][3]*b.matrix[3][1];
			c.matrix[1][2]=a.matrix[1][0]*b.matrix[0][2]+a.matrix[1][1]*b.matrix[1][2]+a.matrix[1][2]*b.matrix[2][2]+a.matrix[1][3]*b.matrix[3][2];
			c.matrix[1][3]=a.matrix[1][0]*b.matrix[0][3]+a.matrix[1][1]*b.matrix[1][3]+a.matrix[1][2]*b.matrix[2][3]+a.matrix[1][3]*b.matrix[3][3];
			c.matrix[2][0]=a.matrix[2][0]*b.matrix[0][0]+a.matrix[2][1]*b.matrix[1][0]+a.matrix[2][2]*b.matrix[2][0]+a.matrix[2][3]*b.matrix[3][0];
			c.matrix[2][1]=a.matrix[2][0]*b.matrix[0][1]+a.matrix[2][1]*b.matrix[1][1]+a.matrix[2][2]*b.matrix[2][1]+a.matrix[2][3]*b.matrix[3][1];
			c.matrix[2][2]=a.matrix[2][0]*b.matrix[0][2]+a.matrix[2][1]*b.matrix[1][2]+a.matrix[2][2]*b.matrix[2][2]+a.matrix[2][3]*b.matrix[3][2];
			c.matrix[2][3]=a.matrix[2][0]*b.matrix[0][3]+a.matrix[2][1]*b.matrix[1][3]+a.matrix[2][2]*b.matrix[2][3]+a.matrix[2][3]*b.matrix[3][3];
			return c;
		}

		public idx3d_vector matrixvectorproduct(idx3d_matrix b, idx3d_vector a)
		{
			idx3d_vector c=new idx3d_vector();
			c.x=a.x*b.matrix[0][0]+a.y*b.matrix[0][1]+a.z*b.matrix[0][2]+b.matrix[0][3];
			c.y=a.x*b.matrix[1][0]+a.y*b.matrix[1][1]+a.z*b.matrix[1][2]+b.matrix[1][3];
			c.z=a.x*b.matrix[2][0]+a.y*b.matrix[2][1]+a.z*b.matrix[2][2]+b.matrix[2][3];
			return c;
		}

	// DATA MANAGEMENT
		
		public void addObject(int rendermode, int color)
		{
			objects+=1;

			if (objects>=maxobjects)
			{
				if (object==null)
				{
					maxobjects=2;
					object=new idx3d_object[11];
				}
				else
				{
					maxobjects*=2;
					idx3d_object temp[]=new idx3d_object[maxobjects+1];
					System.arraycopy(object,1,temp,1,objects);
					object=temp;
				}
			}
			object[objects]=new idx3d_object(rendermode,color);
		}

		public void addLight(idx3d_vector v, int mode, int intensity)
		{
			lights+=1;

			if (lights>=maxlights)
			{
				if (light==null)
				{
					maxlights=10;
					light=new idx3d_light[11];
				}
				else
				{
					maxlights+=10;
					idx3d_light temp[]=new idx3d_light[maxlights+1];
					System.arraycopy(light,1,temp,1,lights);
					light=temp;
				}
			}
			if (mode==1) v=normalize(v);
			light[lights]=new idx3d_light(v,mode,intensity);
		}

		public void addTexture(Image img)
		{
			textures+=1;

			if (textures>=maxtextures)
			{
				if (texture==null)
				{
					maxtextures=20;
					texture=new idx3d_texture[21];
				}
				else
				{
					maxtextures*=2;
					idx3d_texture temp[]=new idx3d_texture[maxtextures+1];
					System.arraycopy(texture,1,temp,1,textures);
					texture=temp;
				}
			}
			texture[textures]=new idx3d_texture();
			while (img.getWidth(this)<0 || img.getHeight(this)<0){}
			int width=img.getWidth(this);
			int height=img.getHeight(this);
			texture[textures].w=width;
			texture[textures].h=height;
			texture[textures].pixel=new int[width][height];
			int temp2[]=new int[width*height];
			PixelGrabber pg= new PixelGrabber(img,0,0,width,height,temp2,0,width);
			try{pg.grabPixels();}
			catch (InterruptedException e){}
			for (int j=0;j<height;j++)
			{
				for (int i=0;i<width;i++)
				{
					texture[textures].pixel[i][j]=temp2[i+j*width];
				}
			}
		}

		public void addTriangle(int objectNr, int p1, int p2, int p3)
		{
			object[objectNr].triangles+=1;

			if (object[objectNr].triangles>=object[objectNr].maxtriangles)
			{
				if (object[objectNr].triangle==null)
				{
					object[objectNr].maxtriangles=2;
					object[objectNr].triangle=new idx3d_triangle[11];
				}
				else
				{
					object[objectNr].maxtriangles*=2;
					idx3d_triangle temp[]=new idx3d_triangle[object[objectNr].maxtriangles+1];
					System.arraycopy(object[objectNr].triangle,1,temp,1,object[objectNr].triangles);
					object[objectNr].triangle=temp;
				}
			}
			object[objectNr].triangle[object[objectNr].triangles]=new idx3d_triangle(p1,p2,p3);
			object[objectNr].triangle[object[objectNr].triangles].n=getNormal(object[objectNr].node[p1].v,object[objectNr].node[p3].v,object[objectNr].node[p2].v);
		}

		public void addNode(int objectNr, idx3d_node t)
		{
			object[objectNr].nodes+=1;

			if (object[objectNr].nodes>=object[objectNr].maxnodes)
			{
				if (object[objectNr].node==null)
				{
					object[objectNr].maxnodes=2;
					object[objectNr].node=new idx3d_node[11];
				}
				else
				{
					object[objectNr].maxnodes*=2;
					idx3d_node temp[]=new idx3d_node[object[objectNr].maxnodes+1];
					System.arraycopy(object[objectNr].node,1,temp,1,object[objectNr].nodes);
					object[objectNr].node=temp;
				}
			}
			t.n=normalize(t.n);
			object[objectNr].node[object[objectNr].nodes]=t;
		}

		public void addNode(int objectNr,float x, float y, float z)
		{
			addNode(objectNr,new idx3d_node(x,y,z));
		}

	// OBJECT MANIPULATION

		public idx3d_matrix shiftMatrix(float dx, float dy, float dz)
		{
			idx3d_matrix m =new idx3d_matrix();
			m.matrix[0][3]=dx;
			m.matrix[1][3]=dy;
			m.matrix[2][3]=dz;
			return m;
		}

		public idx3d_matrix scaleMatrix(float dx, float dy, float dz)
		{
			idx3d_matrix m =new idx3d_matrix();
			m.matrix[0][0]=dx;
			m.matrix[1][1]=dy;
			m.matrix[2][2]=dz;
			return m;
		}

		public idx3d_matrix rotateMatrix(float dx, float dy, float dz)
		{
			float SIN;
			float COS;
			int angle;
			idx3d_matrix out=new idx3d_matrix();

			if (dx!=0)
			{
				idx3d_matrix m =new idx3d_matrix();
				angle=((int)dx+1440)%360;
				SIN=sinus[angle];
				COS=cosinus[angle];
				m.matrix[1][1]=COS;
				m.matrix[1][2]=SIN;
				m.matrix[2][1]=-SIN;
				m.matrix[2][2]=COS;
				out=crossproduct(m,out);
			}
			if (dy!=0)
			{
				idx3d_matrix m =new idx3d_matrix();
				angle=((int)dy+720)%360;
				SIN=sinus[angle];
				COS=cosinus[angle];
				m.matrix[0][0]=COS;
				m.matrix[0][2]=SIN;
				m.matrix[2][0]=-SIN;
				m.matrix[2][2]=COS;
				out=crossproduct(m,out);
			}
			if (dz!=0)
			{
				idx3d_matrix m =new idx3d_matrix();
				angle=((int)dz+720)%360;
				SIN=sinus[angle];
				COS=cosinus[angle];
				m.matrix[0][0]=COS;
				m.matrix[0][1]=SIN;
				m.matrix[1][0]=-SIN;
				m.matrix[1][1]=COS;
				out=crossproduct(m,out);
			}
			return out;
		}

		public void shiftObject(int o, float dx, float dy, float dz)
		{
			object[o].matrix=crossproduct(shiftMatrix(dx,dy,dz),object[o].matrix);
		}

		public void scaleObject(int o, float dx, float dy, float dz)
		{
			object[o].matrix=crossproduct(scaleMatrix(dx,dy,dz),object[o].matrix);
		}

		public void rotateObject(int o, float dx, float dy, float dz)
		{
			object[o].matrix=crossproduct(object[o].matrix,rotateMatrix(dx,dy,dz));
		}

		public void rotateObjectWorld(int o, float dx, float dy, float dz)
		{
			object[o].matrix=crossproduct(rotateMatrix(dx,dy,dz),object[o].matrix);
		}

		public void rotateObject(int obj, float px, float py, float pz, float dx, float dy, float dz)
		{
			shiftObject(obj,-px,-py,-pz);
			rotateObject(obj,dx,dy,dz);
			shiftObject(obj,px,py,pz);
		}

		public void shiftWorld(float dx, float dy, float dz)
		{
			worldmatrix=crossproduct(shiftMatrix(dx,dy,dz),worldmatrix);
		}

		public void scaleWorld(float dx, float dy, float dz)
		{
			worldmatrix=crossproduct(scaleMatrix(dx,dy,dz),worldmatrix);
		}

		public void rotateWorld(float dx, float dy, float dz)
		{
			worldmatrix=crossproduct(rotateMatrix(dx,dy,dz),worldmatrix);
		}

		public void scaleObject(int obj, float d)
		{
			scaleObject(obj,d,d,d);
		}

		public void scaleWorld(float d)
		{
			scaleWorld(d,d,d);
		}

	// RENDERING

		public Image renderScene()
		{
			clearDoubleBuffer();
			paintObjects();
			paintImage();
			return DoubleBuffer;
		}	

		private void clearDoubleBuffer()
		{
			for (int j=0;j<h;j++)
			{
				for (int i=0;i<w;i++)
				{
					zBuffer[i][j]=zInfinite;
				}
			}
			for (int i=0;i<w*h;i++)
			{
				TargetPixel[i]=Background[i];
			}
		}

		private void clearBackground()
		{
			for (int i=0;i<w*h;i++)
			{
					Background[i]=bgcolor;
			}
		}

		private void paintObjects()
		{
			for(int i=1;i<=objects;i++)
			{
				object[i].matrix2=crossproduct(worldmatrix,object[i].matrix);
				for(int j=1;j<=object[i].nodes;j++)
				{
					object[i].node[j]=projectNode(i,object[i].node[j]);
				}
				for(int j=1;j<=object[i].triangles;j++)
				{
					if (object[i].mode==1) drawWireframe(i,object[i].triangle[j]);
					if (object[i].mode==2) drawFlatshaded(i,object[i].triangle[j]);
					if (object[i].mode==3) drawGouraud(i,object[i].triangle[j]);
					if (object[i].mode==4) drawPhong(i,object[i].triangle[j]);
					if (object[i].mode==5) drawEnvmapped(i,object[i].triangle[j]);
					if (object[i].mode==6) drawGouraudTexture(i,object[i].triangle[j]);
					if (object[i].mode==7) drawPhongTexture(i,object[i].triangle[j]);
					if (object[i].mode==8) drawEnvmappedTexture(i,object[i].triangle[j]);
				}
			}
		}

		private void paintImage()
		{
			DoubleBuffer=createImage(new MemoryImageSource(w,h,idx_cm,TargetPixel,0,w));
		}

		private idx3d_node projectNode(int obj, idx3d_node q)
		{
			idx3d_node w=q;
			idx3d_vector mvect=matrixvectorproduct(object[obj].matrix2,w.v);
			w.n2=normalize(matrixvectorproduct(object[obj].matrix2,w.n));

			float zoom=perspective/(perspective+mvect.z)*zoomcorrection;
			w.xx=(int)(mvect.x*zoom*centerx+centerx);
			w.yy=(int)(mvect.y*zoom*centery+centery);
			w.zz=(int)(mvect.z*65536);
			return w;
		}

		private int getIntensity(int nx, int ny)
		{
			if (staticLight) return StaticLightMap[nx/2+127][ny/2+127];
			int nz=LightMap[nx/2+127][ny/2+127];
			int intensity=ambient;
			for (int i=1;i<=lights;i++)
			{
				if (light[i].mode==1)
				{
					intensity+=crop((light[i].intensity*(int)Math.abs(nx*light[i].x+ny*light[i].y+nz*light[i].z)>>8)>>8,0,255);
				}
				if (light[i].mode==2)
				{
					// POINT LIGHT ALGORITHM TO BE IMPLEMENTED HERE
				}
			}
			return crop(intensity,0,255);
		}

		private int getIntensity(idx3d_vector v)
		{
			return getIntensity((int)(v.x*255),(int)(v.y*255));
		}

		private int getEnvironment(int nx, int ny)
		{
			if (staticLight) return StaticEnvMap[nx/2+127][ny/2+127];
			int env=EnvMap[nx/2+127][ny/2+127];
			return crop(ambient+getIntensity(nx,ny)*env*reflectivity/65536,0,255);
		}

		public int getIntColor(int r, int g, int b)
		{
			return alpha|(r<<16)|(g<<8)|b;
		}

		private int getColor(int c, int intensity)
		{
			int r=ColorMap[intensity][(rbit&c)>>16];
			int g=ColorMap[intensity][(gbit&c)>>8];
			int b=ColorMap[intensity][(bbit&c)];
			return getIntColor(r,g,b);
		}

		private int getGray(int c)
		{
			return (((rbit&c)>>16)+((gbit&c)>>8)+(bbit&c))/3;
			//return (bbit&c);
		}

		public void setBackground(Image TempImage)
		{
			PixelGrabber pg= new PixelGrabber(TempImage,0,0,w,h,Background,0,w);
			try{pg.grabPixels();}
			catch (InterruptedException e){}
		}

		public void setEnvironment(Image TempImage)
		{
			int temp[]=new int[256*256];
			PixelGrabber pg= new PixelGrabber(TempImage,0,0,256,256,temp,0,256);
			try{pg.grabPixels();}
			catch (InterruptedException e){}
			for (int i=0;i<256;i++)
			{
				for (int j=0;j<256;j++)
				{
					EnvMap[i][j]=getGray(temp[i+j*256]);
				}
			}
		}

	// RENDERMODES

		private void drawLine(idx3d_node q1,idx3d_node q2,int color)
		{
			idx3d_node temp;
			int dx=(int)Math.abs(q1.xx-q2.xx);
			int dy=(int)Math.abs(q1.yy-q2.yy);
			int dz=0;
			int x,y,z;
			int x2,y2;

			if (dx>dy)
			{
				if (q1.xx>q2.xx)
				{
					temp=q1;
					q1=q2;
					q2=temp;
				}
				if (dx>0)
				{
					dz=(q2.zz-q1.zz)/dx;
					dy=((q2.yy-q1.yy)<<16)/dx;
				}
				z=q1.zz;
				y=q1.yy<<16;
				for(x=q1.xx;x<=q2.xx;x++)
				{
					y2=y>>16;
					if (inrange(x,0,w)&&inrange(y2,0,h))
					{
						if (z<zBuffer[x][y2])
						{
							TargetPixel[x+y2*w]=color;
							zBuffer[x][y2]=z;
						}
					}
					z+=dz;
					y+=dy;
				}
			}
			else
			{
				if (q1.yy>q2.yy)
				{
					temp=q1;
					q1=q2;
					q2=temp;
				}
				if (dy>0)
				{
					dz=(q2.zz-q1.zz)/dy;
					dx=((q2.xx-q1.xx)<<16)/dy;
				}
				z=q1.zz;
				x=q1.xx<<16;
				for(y=q1.yy;y<=q2.yy;y++)
				{
					x2=x>>16;
					if (inrange(x2,0,w)&&inrange(y,0,h))
					{
						if (z<zBuffer[x2][y])
						{
							TargetPixel[x2+y*w]=color;
							zBuffer[x2][y]=z;
						}
					}
					z+=dz;
					x+=dx;
				}
			}
		}

		private void drawWireframe(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;
			int c=object[obj].color;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			drawLine(p1,p2,c);
			drawLine(p2,p3,c);
			drawLine(p1,p3,c);
		}

		private void drawFlatshaded(int obj, idx3d_triangle t)
		{	
			idx3d_vector n=normalize(matrixvectorproduct(object[obj].matrix,t.n));
			
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;				
			}
			xL=x2;
			zL=p2.zz;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;			
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawFlatshadedLine(j,getColor(object[obj].color,getIntensity(n)));
		}

		private void drawGouraud(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int i1=getIntensity(p1.n2)<<8;
			int i2=getIntensity(p2.n2)<<8;
			int i3=getIntensity(p3.n2)<<8;

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int di1=0;
			int di2=0;
			int di3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				di1=(i2-i1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				di2=(i3-i2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				di3=(i3-i1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int iL=i1;
			int iR=i1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					IEdgeBufferL[k]=iL;
					IEdgeBufferR[k]=iR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				iL+=di1;
				iR+=di3;
			}
			xL=x2;
			zL=p2.zz;
			iL=i2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					IEdgeBufferL[k]=iL;
					IEdgeBufferR[k]=iR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				iL+=di2;
				iR+=di3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawGouraudLine(j,object[obj].color);
		}

		private void drawPhong(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;
			
			int nx1=(int)(65536*p1.n2.x);
			int nx2=(int)(65536*p2.n2.x);
			int nx3=(int)(65536*p3.n2.x);
			int ny1=(int)(65536*p1.n2.y);
			int ny2=(int)(65536*p2.n2.y);
			int ny3=(int)(65536*p3.n2.y);

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int dnx1=0;
			int dnx2=0;
			int dnx3=0;
			int dny1=0;
			int dny2=0;
			int dny3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				dnx1=(nx2-nx1)/dy;
				dny1=(ny2-ny1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				dnx2=(nx3-nx2)/dy;
				dny2=(ny3-ny2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				dnx3=(nx3-nx1)/dy;
				dny3=(ny3-ny1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int nxL=nx1;
			int nxR=nx1;
			int nyL=ny1;
			int nyR=ny1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				nxL+=dnx1;
				nxR+=dnx3;
				nyL+=dny1;
				nyR+=dny3;
			}
			xL=x2;
			zL=p2.zz;
			nxL=nx2;
			nyL=ny2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				nxL+=dnx2;
				nxR+=dnx3;
				nyL+=dny2;
				nyR+=dny3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawPhongLine(j,object[obj].color);
		}

		private void drawEnvmapped(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;
			
			int nx1=(int)(65536*p1.n2.x);
			int nx2=(int)(65536*p2.n2.x);
			int nx3=(int)(65536*p3.n2.x);
			int ny1=(int)(65536*p1.n2.y);
			int ny2=(int)(65536*p2.n2.y);
			int ny3=(int)(65536*p3.n2.y);

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int dnx1=0;
			int dnx2=0;
			int dnx3=0;
			int dny1=0;
			int dny2=0;
			int dny3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				dnx1=(nx2-nx1)/dy;
				dny1=(ny2-ny1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				dnx2=(nx3-nx2)/dy;
				dny2=(ny3-ny2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				dnx3=(nx3-nx1)/dy;
				dny3=(ny3-ny1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int nxL=nx1;
			int nxR=nx1;
			int nyL=ny1;
			int nyR=ny1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				nxL+=dnx1;
				nxR+=dnx3;
				nyL+=dny1;
				nyR+=dny3;
			}
			xL=x2;
			zL=p2.zz;
			nxL=nx2;
			nyL=ny2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				nxL+=dnx2;
				nxR+=dnx3;
				nyL+=dny2;
				nyR+=dny3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawEnvmappedLine(j,object[obj].color);
		}

		private void drawGouraudTexture(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int i1=getIntensity(p1.n2)<<8;
			int i2=getIntensity(p2.n2)<<8;
			int i3=getIntensity(p3.n2)<<8;

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;
			float tw=(float)texture[object[obj].texture].w*65536;
			float th=(float)texture[object[obj].texture].h*65536;
			int tx1=(int)(tw*p1.tx);
			int tx2=(int)(tw*p2.tx);
			int tx3=(int)(tw*p3.tx);
			int ty1=(int)(th*p1.ty);
			int ty2=(int)(th*p2.ty);
			int ty3=(int)(th*p3.ty);

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int di1=0;
			int di2=0;
			int di3=0;
			int dtx1=0;
			int dtx2=0;
			int dtx3=0;
			int dty1=0;
			int dty2=0;
			int dty3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				di1=(i2-i1)/dy;
				dtx1=(tx2-tx1)/dy;
				dty1=(ty2-ty1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				di2=(i3-i2)/dy;
				dtx2=(tx3-tx2)/dy;
				dty2=(ty3-ty2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				di3=(i3-i1)/dy;
				dtx3=(tx3-tx1)/dy;
				dty3=(ty3-ty1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int iL=i1;
			int iR=i1;
			int txL=tx1;
			int txR=tx1;
			int tyL=ty1;
			int tyR=ty1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					IEdgeBufferL[k]=iL;
					IEdgeBufferR[k]=iR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				iL+=di1;
				iR+=di3;
				txL+=dtx1;
				txR+=dtx3;
				tyL+=dty1;
				tyR+=dty3;
			}
			xL=x2;
			zL=p2.zz;
			iL=i2;
			txL=tx2;
			tyL=ty2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					IEdgeBufferL[k]=iL;
					IEdgeBufferR[k]=iR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				iL+=di2;
				iR+=di3;
				txL+=dtx2;
				txR+=dtx3;
				tyL+=dty2;
				tyR+=dty3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawGouraudTextureLine(j,object[obj].texture);
		}

		private void drawPhongTexture(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;

			int nx1=(int)(65536*p1.n2.x);
			int nx2=(int)(65536*p2.n2.x);
			int nx3=(int)(65536*p3.n2.x);
			int ny1=(int)(65536*p1.n2.y);
			int ny2=(int)(65536*p2.n2.y);
			int ny3=(int)(65536*p3.n2.y);

			float tw=(float)texture[object[obj].texture].w*65536;
			float th=(float)texture[object[obj].texture].h*65536;
			int tx1=(int)(tw*p1.tx);
			int tx2=(int)(tw*p2.tx);
			int tx3=(int)(tw*p3.tx);
			int ty1=(int)(th*p1.ty);
			int ty2=(int)(th*p2.ty);
			int ty3=(int)(th*p3.ty);

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int dnx1=0;
			int dnx2=0;
			int dnx3=0;
			int dny1=0;
			int dny2=0;
			int dny3=0;
			int dtx1=0;
			int dtx2=0;
			int dtx3=0;
			int dty1=0;
			int dty2=0;
			int dty3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				dnx1=(nx2-nx1)/dy;
				dny1=(ny2-ny1)/dy;
				dtx1=(tx2-tx1)/dy;
				dty1=(ty2-ty1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				dnx2=(nx3-nx2)/dy;
				dny2=(ny3-ny2)/dy;
				dtx2=(tx3-tx2)/dy;
				dty2=(ty3-ty2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				dnx3=(nx3-nx1)/dy;
				dny3=(ny3-ny1)/dy;
				dtx3=(tx3-tx1)/dy;
				dty3=(ty3-ty1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int nxL=nx1;
			int nxR=nx1;
			int nyL=ny1;
			int nyR=ny1;
			int txL=tx1;
			int txR=tx1;
			int tyL=ty1;
			int tyR=ty1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				nxL+=dnx1;
				nxR+=dnx3;
				nyL+=dny1;
				nyR+=dny3;
				txL+=dtx1;
				txR+=dtx3;
				tyL+=dty1;
				tyR+=dty3;
			}
			xL=x2;
			zL=p2.zz;
			nxL=nx2;
			nyL=ny2;
			txL=tx2;
			tyL=ty2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				nxL+=dnx2;
				nxR+=dnx3;
				nyL+=dny2;
				nyR+=dny3;
				txL+=dtx2;
				txR+=dtx3;
				tyL+=dty2;
				tyR+=dty3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawPhongTextureLine(j,object[obj].texture);
		}

		private void drawEnvmappedTexture(int obj, idx3d_triangle t)
		{	
			int temp;
			idx3d_node temp1;

			idx3d_node p1=object[obj].node[t.p1];
			idx3d_node p2=object[obj].node[t.p2];
			idx3d_node p3=object[obj].node[t.p3];

			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}
			if (p2.yy>p3.yy)
			{
				temp1=p2;
				p2=p3;
				p3=temp1;
			}
			if (p1.yy>p2.yy)
			{
				temp1=p1;
				p1=p2;
				p2=temp1;
			}

			int x1=p1.xx<<16;
			int x2=p2.xx<<16;
			int x3=p3.xx<<16;
			int y1=p1.yy<<16;
			int y2=p2.yy<<16;
			int y3=p3.yy<<16;

			int nx1=(int)(65536*p1.n2.x);
			int nx2=(int)(65536*p2.n2.x);
			int nx3=(int)(65536*p3.n2.x);
			int ny1=(int)(65536*p1.n2.y);
			int ny2=(int)(65536*p2.n2.y);
			int ny3=(int)(65536*p3.n2.y);

			float tw=(float)texture[object[obj].texture].w*65536;
			float th=(float)texture[object[obj].texture].h*65536;
			int tx1=(int)(tw*p1.tx);
			int tx2=(int)(tw*p2.tx);
			int tx3=(int)(tw*p3.tx);
			int ty1=(int)(th*p1.ty);
			int ty2=(int)(th*p2.ty);
			int ty3=(int)(th*p3.ty);

			int dx1=0;
			int dx2=0;
			int dx3=0;			
			int dz1=0;
			int dz2=0;
			int dz3=0;
			int dnx1=0;
			int dnx2=0;
			int dnx3=0;
			int dny1=0;
			int dny2=0;
			int dny3=0;
			int dtx1=0;
			int dtx2=0;
			int dtx3=0;
			int dty1=0;
			int dty2=0;
			int dty3=0;
			int dy;
			if (y2!=y1) 
			{
				dy=(y2-y1)>>16;
				dx1=(x2-x1)/dy;
				dz1=(p2.zz-p1.zz)/dy;
				dnx1=(nx2-nx1)/dy;
				dny1=(ny2-ny1)/dy;
				dtx1=(tx2-tx1)/dy;
				dty1=(ty2-ty1)/dy;
			}
			if (y3!=y2) 
			{
				dy=(y3-y2)>>16;
				dx2=(x3-x2)/dy;
				dz2=(p3.zz-p2.zz)/dy;
				dnx2=(nx3-nx2)/dy;
				dny2=(ny3-ny2)/dy;
				dtx2=(tx3-tx2)/dy;
				dty2=(ty3-ty2)/dy;
			}
			if (y3!=y1) 
			{
				dy=(y3-y1)>>16;
				dx3=(x3-x1)/dy;
				dz3=(p3.zz-p1.zz)/dy;
				dnx3=(nx3-nx1)/dy;
				dny3=(ny3-ny1)/dy;
				dtx3=(tx3-tx1)/dy;
				dty3=(ty3-ty1)/dy;
			}
			
			int xL=x1;
			int xR=x1;
			int zL=p1.zz;
			int zR=p1.zz;
			int nxL=nx1;
			int nxR=nx1;
			int nyL=ny1;
			int nyR=ny1;
			int txL=tx1;
			int txR=tx1;
			int tyL=ty1;
			int tyR=ty1;

			y1=y1>>16;
			y2=y2>>16;
			y3=y3>>16;

			for (int k=y1;k<y2;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx1;
				xR+=dx3;
				zL+=dz1;
				zR+=dz3;
				nxL+=dnx1;
				nxR+=dnx3;
				nyL+=dny1;
				nyR+=dny3;
				txL+=dtx1;
				txR+=dtx3;
				tyL+=dty1;
				tyR+=dty3;
			}
			xL=x2;
			zL=p2.zz;
			nxL=nx2;
			nyL=ny2;
			txL=tx2;
			tyL=ty2;
			for (int k=y2;k<=y3;k++)
			{
				if ((k>=0)&&(k<h))
				{
					LEdgeBuffer[k]=xL>>16;
					REdgeBuffer[k]=xR>>16;
					ZEdgeBufferL[k]=zL;
					ZEdgeBufferR[k]=zR;
					NXEdgeBufferL[k]=nxL;
					NXEdgeBufferR[k]=nxR;
					NYEdgeBufferL[k]=nyL;
					NYEdgeBufferR[k]=nyR;
					TXEdgeBufferL[k]=txL;
					TXEdgeBufferR[k]=txR;
					TYEdgeBufferL[k]=tyL;
					TYEdgeBufferR[k]=tyR;
				}
				xL+=dx2;
				xR+=dx3;
				zL+=dz2;
				zR+=dz3;		
				nxL+=dnx2;
				nxR+=dnx3;
				nyL+=dny2;
				nyR+=dny3;
				txL+=dtx2;
				txR+=dtx3;
				tyL+=dty2;
				tyR+=dty3;
			}		

			y1=crop(y1,0,h);
			y3=crop(y3,0,h);

			for (int j=y1;j<=y3;j++) drawEnvmappedTextureLine(j,object[obj].texture);
		}
					

		private void drawFlatshadedLine(int y, int color)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp;
						temp=xL; xL=xR; xR=temp;
						temp=zL; zL=zR;	zR=temp;
					}					
					int dz=(zR-zL)/(xR-xL);
					int z=zL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							TargetPixel[x+offset]=color;
							zBuffer[x][y]=z;
						}
						z+=dz;
					}
				}
			}
		}

		private void drawGouraudLine(int y, int color)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int iL=IEdgeBufferL[y];
				int iR=IEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp;
						temp=xL; xL=xR; xR=temp;
						temp=zL; zL=zR;	zR=temp;
						temp=iL; iL=iR; iR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int di=(iR-iL)/dx;
					int z=zL;
					int i=iL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							TargetPixel[x+offset]=getColor(color,i>>8);
							zBuffer[x][y]=z;
						}
						z+=dz;
						i+=di;
					}
				}
			}
		}

		private void drawPhongLine(int y, int color)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int nxL=NXEdgeBufferL[y];
				int nxR=NXEdgeBufferR[y];
				int nyL=NYEdgeBufferL[y];
				int nyR=NYEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp; 
						temp=xL; xL=xR; xR=temp; 
						temp=zL; zL=zR; zR=temp; 
						temp=nxL; nxL=nxR; nxR=temp;
						temp=nyL; nyL=nyR; nyR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int dnx=(nxR-nxL)/dx;
					int dny=(nyR-nyL)/dx;
					int z=zL;
					int nx=nxL;
					int ny=nyL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							TargetPixel[x+offset]=getColor(color,getIntensity(nx/256,ny/256));
							zBuffer[x][y]=z;
						}
						z+=dz;
						nx+=dnx;
						ny+=dny;
					}
				}
			}
		}

		private void drawEnvmappedLine(int y, int color)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int nxL=NXEdgeBufferL[y];
				int nxR=NXEdgeBufferR[y];
				int nyL=NYEdgeBufferL[y];
				int nyR=NYEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp; 
						temp=xL; xL=xR; xR=temp; 
						temp=zL; zL=zR; zR=temp; 
						temp=nxL; nxL=nxR; nxR=temp;
						temp=nyL; nyL=nyR; nyR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int dnx=(nxR-nxL)/dx;
					int dny=(nyR-nyL)/dx;
					int z=zL;
					int nx=nxL;
					int ny=nyL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							TargetPixel[x+offset]=getColor(color,getEnvironment(nx/256,ny/256));
							zBuffer[x][y]=z;
						}
						z+=dz;
						nx+=dnx;
						ny+=dny;
					}
				}
			}
		}

		private void drawGouraudTextureLine(int y, int t)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int iL=IEdgeBufferL[y];
				int iR=IEdgeBufferR[y];
				int txL=TXEdgeBufferL[y];
				int txR=TXEdgeBufferR[y];
				int tyL=TYEdgeBufferL[y];
				int tyR=TYEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp;
						temp=xL; xL=xR; xR=temp;
						temp=zL; zL=zR;	zR=temp;
						temp=iL; iL=iR; iR=temp;
						temp=txL; txL=txR; txR=temp;
						temp=tyL; tyL=tyR; tyR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int di=(iR-iL)/dx;
					int dtx=(txR-txL)/dx;
					int dty=(tyR-tyL)/dx;
					int z=zL;
					int i=iL;
					int tx=txL;
					int ty=tyL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							tx=(int)Math.abs(tx);
							ty=(int)Math.abs(ty);
							TargetPixel[x+offset]=getColor(texture[t].pixel[(tx>>16)%texture[t].w][(ty>>16)%texture[t].h],i>>8);
							zBuffer[x][y]=z;
						}
						z+=dz;
						i+=di;
						tx+=dtx;
						ty+=dty;
					}
				}
			}
		}

		private void drawPhongTextureLine(int y, int t)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int nxL=NXEdgeBufferL[y];
				int nxR=NXEdgeBufferR[y];
				int nyL=NYEdgeBufferL[y];
				int nyR=NYEdgeBufferR[y];
				int txL=TXEdgeBufferL[y];
				int txR=TXEdgeBufferR[y];
				int tyL=TYEdgeBufferL[y];
				int tyR=TYEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp;
						temp=xL; xL=xR; xR=temp;
						temp=zL; zL=zR;	zR=temp;
						temp=nxL; nxL=nxR; nxR=temp;
						temp=nyL; nyL=nyR; nyR=temp;
						temp=txL; txL=txR; txR=temp;
						temp=tyL; tyL=tyR; tyR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int dtx=(txR-txL)/dx;
					int dty=(tyR-tyL)/dx;
					int dnx=(nxR-nxL)/dx;
					int dny=(nyR-nyL)/dx;
					int z=zL;
					int nx=nxL;
					int ny=nyL;
					int tx=txL;
					int ty=tyL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							tx=(int)Math.abs(tx);
							ty=(int)Math.abs(ty);
							TargetPixel[x+offset]=getColor(texture[t].pixel[(tx>>16)%texture[t].w][(ty>>16)%texture[t].h],getIntensity(nx/256,ny/256));
							zBuffer[x][y]=z;
						}
						z+=dz;
						nx+=dnx;
						ny+=dny;
						tx+=dtx;
						ty+=dty;
					}
				}
			}
		}

		private void drawEnvmappedTextureLine(int y, int t)
		{
			if ((y>=0)&&(y<h))
			{	
				int xL=LEdgeBuffer[y];
				int xR=REdgeBuffer[y];
				int zL=ZEdgeBufferL[y];
				int zR=ZEdgeBufferR[y];
				int nxL=NXEdgeBufferL[y];
				int nxR=NXEdgeBufferR[y];
				int nyL=NYEdgeBufferL[y];
				int nyR=NYEdgeBufferR[y];
				int txL=TXEdgeBufferL[y];
				int txR=TXEdgeBufferR[y];
				int tyL=TYEdgeBufferL[y];
				int tyR=TYEdgeBufferR[y];
				if ((xL!=xR))
				{
					if (xL>xR)
					{
						int temp;
						temp=xL; xL=xR; xR=temp;
						temp=zL; zL=zR;	zR=temp;
						temp=nxL; nxL=nxR; nxR=temp;
						temp=nyL; nyL=nyR; nyR=temp;
						temp=txL; txL=txR; txR=temp;
						temp=tyL; tyL=tyR; tyR=temp;
					}	
					int dx=(xR-xL);
					int dz=(zR-zL)/dx;
					int dtx=(txR-txL)/dx;
					int dty=(tyR-tyL)/dx;
					int dnx=(nxR-nxL)/dx;
					int dny=(nyR-nyL)/dx;
					int z=zL;
					int nx=nxL;
					int ny=nyL;
					int tx=txL;
					int ty=tyL;

					xL=crop(xL,0,w);
					xR=crop(xR,0,w);

					int offset=y*w;
							
					for (int x=xL;x<xR;x++)
					{
						if (z<zBuffer[x][y])
						{
							tx=(int)Math.abs(tx);
							ty=(int)Math.abs(ty);
							TargetPixel[x+offset]=getColor(texture[t].pixel[(tx>>16)%texture[t].w][(ty>>16)%texture[t].h],getEnvironment(nx/256,ny/256));
							zBuffer[x][y]=z;
						}
						z+=dz;
						nx+=dnx;
						ny+=dny;
						tx+=dtx;
						ty+=dty;
					}
				}
			}
		}

	// SPECIAL FUNCTIONS TO CREATE OBJECTS

		public void generateField(float data[][],int xmax, int ymax, int rm, int c)
		{
			float xtemp;
			float ytemp;
			float ztemp;
			int q1;
			int q2;
			int q3;
			int q4;
			idx3d_node v;
			addObject(rm,c);
			rotateObject(objects,(float)90,0,0);
			float xscale=2/(float)(xmax-1);
			float yscale=2/(float)(ymax-1);
			for (int i=0; i<xmax; i++)
			{
				for (int j=0; j<ymax; j++)
				{
					xtemp=-1+xscale*(float)i;
					ytemp=-1+yscale*(float)j;
					ztemp=data[i][j];
					v=new idx3d_node(xtemp,ytemp,ztemp);
					if ((j>0)&&(i>0))
					{
						v.n.x=(data[i-1][j]-data[i][j])/xscale;
						v.n.y=(data[i][j-1]-data[i][j])/yscale;
						v.n.z=(float)1;
					}
					v.n.z=(float)1;
					v.tx=(float)i/(float)(xmax-1);
					v.ty=(float)j/(float)(ymax-1);
					addNode(objects,v);
				}
			}
			for (int i=0; i<(xmax-1); i++)
			{
				for (int j=0; j<(ymax-1); j++)
				{
					q1=j+xmax*i+1;
					q2=j+1+xmax*i+1;
					q3=j+xmax*(i+1)+1;
					q4=j+1+xmax*(i+1)+1;

					addTriangle(objects,q1,q2,q3);
					addTriangle(objects,q3,q2,q4);
				}
			}
		}
public void generateScanObject(int obj, int rows, int cols) {
			int start=1;
			int k;
			int sw=0;
			int end=object[obj].nodes+1;
			int t_nodes=object[obj].nodes;
			float tmpx, tmpy, tmpz, dx, dy;

			//normalize the whole thing
			for (int j=0;j<cols;j++)
				for (int i=1;i<rows;i++){
					k=j*rows+i;
					tmpx=object[obj].node[k].v.x;
					tmpz=object[obj].node[k].v.z;
					if (i==1) object[obj].node[k].n=normalize(new idx3d_vector(tmpx,1,tmpz));
					else if (i==rows-1) object[obj].node[k].n=normalize(new idx3d_vector(tmpx,-1,tmpz));
					else {
						dx=object[obj].node[k-1].v.x-object[obj].node[k+1].v.x;
						dy=object[obj].node[k-1].v.y-object[obj].node[k+1].v.y;
						object[obj].node[k].n=normalize(new idx3d_vector(tmpx,dx/dy,tmpz));
					}
					object[obj].node[k].tx=(float)(j)/(float)(cols-1);
					object[obj].node[k].ty=(float)(i-1)/(float)(rows-1);
				}

			// Create triangles
			
			for (int col=0;col<cols;col++){
				for (int row=1;row<rows;row++){
					//don't add triangles if the x or z of it are 0, thus solving the holes
					sw=0;
					k=col*rows+row;
					tmpx=object[obj].node[k].v.x;
					tmpz=object[obj].node[k].v.z;
					if ((tmpx==0)||(tmpz==0)) sw=1;
					k=(col+1)*rows+row;					
					tmpx=object[obj].node[k].v.x;
					tmpz=object[obj].node[k].v.z;
					if ((tmpx==0)||(tmpz==0)) sw=1;
					k=col*rows+row+1;
					tmpx=object[obj].node[k].v.x;
					tmpz=object[obj].node[k].v.z;
					if ((tmpx==0)||(tmpz==0)) sw=1;
					if (sw==0){
						addTriangle(obj,(rows*col+row),(rows*(col+1)+row),(rows*col+row+1));
						addTriangle(obj,(rows*col+row+1),(rows*(col+1)+row),(rows*(col+1)+row+1));
					}
				}
			}
		}


		public void generateRotationObject(int obj, int steps)
		{
			double alpha=2*pi/((double)steps);

			int start=1;
			int end=object[obj].nodes+1;
			int t_nodes=object[obj].nodes;

			//Adjust normals of existing points
			object[obj].node[1].n=normalize(new idx3d_vector(object[obj].node[1].v.x,(float)1,object[obj].node[1].v.z));
			object[obj].node[t_nodes].n=normalize(new idx3d_vector(object[obj].node[t_nodes].v.x,(float)-1,object[obj].node[t_nodes].v.z));
			for (int i=2;i<t_nodes;i++)
			{
				object[obj].node[i].n=normalize(new idx3d_vector(object[obj].node[i].v.x,(object[obj].node[i-1].v.x-object[obj].node[i+1].v.x)/(object[obj].node[i-1].v.y-object[obj].node[i+1].v.y),object[obj].node[i].v.z));
				object[obj].node[i].tx=0;
				object[obj].node[i].ty=(float)(i-1)/(float)(t_nodes-1);
			}

			//Create new nodes
			
			for (int j=1;j<(steps+1);j++)
			{
				for (int i=start;i<end;i++)
				{
					float qx=(float)(object[obj].node[i].v.x*Math.cos(j*alpha)+object[obj].node[i].v.z*Math.sin(j*alpha));
					float qz=(float)(object[obj].node[i].v.z*Math.cos(j*alpha)-object[obj].node[i].v.x*Math.sin(j*alpha));
					idx3d_node w=new idx3d_node(qx,object[obj].node[i].v.y,qz);
					if (i==start) w.n=normalize(new idx3d_vector(qx,1,qz));
					else if (i==end-1) w.n=normalize(new idx3d_vector(qx,-1,qz));
					else w.n=normalize(new idx3d_vector(qx,(object[obj].node[i-1].v.x-object[obj].node[i+1].v.x)/(object[obj].node[i-1].v.y-object[obj].node[i+1].v.y),qz));
					w.ty=(float)(i-1)/(float)(t_nodes-1);
					w.tx=(float)(j-1)/(float)(steps-1);
					addNode(obj,w);
				}
			}

			// Create triangles
			for (int col=0;col<steps;col++)
			{
				for (int row=1;row<t_nodes;row++)
				{
					addTriangle(obj,(t_nodes*col+row),(t_nodes*(col+1)+row),(t_nodes*col+row+1));
					addTriangle(obj,(t_nodes*col+row+1),(t_nodes*(col+1)+row),(t_nodes*(col+1)+row+1));
				}
			}
		}

	// BEMCHMARK

	public String getFPS(int frames)
	{
		long idx_time1;
		long idx_time2;
		double fps;
		Image temp;

		idx_time1=new Date().getTime();

		for (int i=0;i<frames;i++)
		{
			rotateWorld((float)Math.random(),(float)Math.random(),(float)Math.random());
			temp=renderScene();
		}

		idx_time2=new Date().getTime();

		fps=(double)frames/((double)(idx_time2-idx_time1)/1000);
		fps=(double)((int)(fps*100))/100;
		return new String("Benchmark: "+fps+" FPS");
	}
}


class idx3d_matrix
{
	float matrix[][]=new float[4][4];

	public idx3d_matrix()
	{
		matrix[0][0]=(float)1;
		matrix[1][1]=(float)1;
		matrix[2][2]=(float)1;
		matrix[3][3]=(float)1;
	}
}

class idx3d_object
{
	int mode=1;
	int color;

	idx3d_triangle triangle[];
	idx3d_node node[];

	int triangles=0;
	int maxtriangles=0;
	int nodes=0;
	int maxnodes=0;
	int texture;
	idx3d_matrix matrix=new idx3d_matrix();
	idx3d_matrix matrix2=new idx3d_matrix();
	public idx3d_object(int rm, int col)
	{
		mode=rm;
		color=col;
	}
}

class idx3d_node
{
	idx3d_vector v=new idx3d_vector();
	idx3d_vector n=new idx3d_vector();
	idx3d_vector n2=new idx3d_vector();
	int xx;   //Coordinates of the
	int yy;   // screen projection
	int zz;   //distance for z-buffer
	float tx;    // Horizontal texture position (0..1+)
	float ty;    // Vertical texture position   (0..1+)
	public idx3d_node(float x,float y,float z)
	{
		v.x=x;
		v.y=y;
		v.z=z;
		n.x=(float)0;
		n.y=(float)0;
		n.z=(float)1;
	}
}		

class idx3d_triangle
{
	int p1;
	int p2;
	int p3;
	idx3d_vector n=new idx3d_vector();
	
	public idx3d_triangle(int q1,int q2,int q3)
	{
		p1=q1;
		p2=q2;
		p3=q3;
	}		
}

class idx3d_light
{
	int x;
	int y;
	int z;
	int mode=0;              //0=parallel light, 1=point light
	int intensity=255;
	
	public idx3d_light(idx3d_vector lvect, int lmode, int lintensity)
	{
		x=(int)(lvect.x*255);
		y=(int)(lvect.y*255);
		z=(int)(lvect.z*255);
		mode=lmode;
		intensity=lintensity;
	}
}

class idx3d_texture
{
	int w;
	int h;
	int pixel[][];
}
	