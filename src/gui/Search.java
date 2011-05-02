package gui;
public final class Search {
    // Dynamic programming algorithm to solve
    // edge detection problem
    public static void Search( int [ ] coins, int differentCoins,
                int maxChange, int [ ] coinsUsed, int [ ] lastCoin )
    {
        coinsUsed[ 0 ] = 0; lastCoin[ 0 ] = 1;

        for( int cents = 1; cents <= maxChange; cents++ )
        {
            int minCoins = cents;
            int newCoin  = 1;

            for( int j = 0; j < differentCoins; j++ )
            {
                if( coins[ j ] > cents )   // Cannot use coin j
                    continue;
                if( coinsUsed[ cents - coins[ j ] ] + 1 < minCoins )
                {
                    minCoins = coinsUsed[ cents - coins[ j ] ] + 1;
                    newCoin  = coins[ j ];
                }
            }

            coinsUsed[ cents ] = minCoins;
            lastCoin[ cents ]  = newCoin;
        }
    }

    // Simple test program
    public static void main( String [ ] args )
    {
        // The coins and the total amount of change
        int numCoins = 5;
        int [ ] coins = { 1, 5, 10, 21, 25 };
        int change = 17; 


        int [ ] used = new int[ change + 1 ];
        int [ ] last = new int[ change + 1 ];

        Search( coins, numCoins, change, used, last );

        System.out.println( "Best is " + used[ change ] + " coins" );

        for( int i = change; i > 0; )
        {
            System.out.print( last[ i ] + " " );
            i -= last[ i ];
        }
        System.out.println( );
    }
}
