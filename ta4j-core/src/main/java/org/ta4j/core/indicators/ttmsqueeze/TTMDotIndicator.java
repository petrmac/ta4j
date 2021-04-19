package org.ta4j.core.indicators.ttmsqueeze;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelLowerIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelMiddleIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelUpperIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

/**
 *
 * https://school.stockcharts.com/doku.php?id=technical_indicators:ttm_squeeze
 */
public class TTMDotIndicator extends CachedIndicator<Boolean> {

    private final int emaPeriod;
    private final int atrPeriod;
    private final ClosePriceIndicator closePrice;
    private final EMAIndicator ema;
    private final BollingerBandsMiddleIndicator bbMiddle;
    private final BollingerBandsLowerIndicator bbLower;
    private final BollingerBandsUpperIndicator bbUpper;
    private final KeltnerChannelMiddleIndicator keltnerMiddle;
    private final KeltnerChannelLowerIndicator keltnerLower;
    private final KeltnerChannelUpperIndicator keltnerUpper;

    protected TTMDotIndicator(BarSeries series, final int emaPeriod, final double atrMultiplier, final int atrPeriod) {
        super(series);
        this.emaPeriod = emaPeriod;
        this.atrPeriod = atrPeriod;

        closePrice = new ClosePriceIndicator(series);
        ema = new EMAIndicator(closePrice, this.emaPeriod);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, this.emaPeriod);
        bbMiddle = new BollingerBandsMiddleIndicator(ema);
        bbLower = new BollingerBandsLowerIndicator(bbMiddle, standardDeviation);
        bbUpper = new BollingerBandsUpperIndicator(bbMiddle, standardDeviation);
        keltnerMiddle = new KeltnerChannelMiddleIndicator(series, emaPeriod);
        keltnerLower = new KeltnerChannelLowerIndicator(keltnerMiddle, atrMultiplier, this.atrPeriod);
        keltnerUpper = new KeltnerChannelUpperIndicator(keltnerMiddle, atrMultiplier, this.atrPeriod);

    }

    private boolean isBBBelowKeltnerLower(int index) {
        return bbLower.getValue(index).isLessThan(keltnerLower.getValue(index));
    }

    private boolean isBBAboveKeltnerUpper(int index) {
        return bbUpper.getValue(index).isGreaterThan(keltnerUpper.getValue(index));
    }


    /**
     *
     * @param index the bar index
     * @return true if the price is not squeezed (Bollinger broken out of Keltner channel)
     */
    @Override
    protected Boolean calculate(int index) {
        return isBBAboveKeltnerUpper(index) || isBBBelowKeltnerLower(index);
    }
}
