package com.cpm.Nestle.blurlockview;

import android.view.animation.Interpolator;

import com.cpm.Nestle.blurlockview.Eases.EaseType;


/**
 * Created by Weiping on 2016/3/17.
 */
public class InterpolatorFactory {

    public static BLVInterpolator getInterpolator(EaseType easeType) {
        return new BLVInterpolator(easeType);
    }

    public static class BLVInterpolator implements Interpolator {

        private EaseType easeType;

        public BLVInterpolator(EaseType easeType) {
            this.easeType = easeType;
        }

        @Override
        public float getInterpolation(float input) {
            return easeType.getOffset(input);
        }
    }

}
