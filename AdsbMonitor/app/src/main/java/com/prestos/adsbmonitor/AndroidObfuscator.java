package com.prestos.adsbmonitor;

import android.graphics.Color;

/**
 * Created by prestos on 22/01/2016.
 * <p/>
 * Mocking Hell!
 * This exists purely to remove the static calls from the main classes, so that they can be mocked out.  Static calls can not be mocked out and make it difficult to unit test
 * Keep it simple as it will be nigh on impossible to unit test
 */
public class AndroidObfuscator {

    /**
     * This is how you spell 'colour'. End of!
     *
     * @param red   Red
     * @param green Green
     * @param blue  Blue
     * @return A lovely rainbow integer, with exquisite cheekbones
     */
    public int getColour(int red, int green, int blue) {
        return Color.rgb(red, green, blue);
    }

}
