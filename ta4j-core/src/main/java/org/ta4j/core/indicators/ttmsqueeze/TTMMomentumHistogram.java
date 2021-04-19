package org.ta4j.core.indicators.ttmsqueeze;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.donchian.DonchianMiddleIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

/**
 *
 * https://school.stockcharts.com/doku.php?id=technical_indicators:ttm_squeeze
 */
public class TTMMomentumHistogram extends CachedIndicator<Num> {

    private DonchianMiddleIndicator donchianMiddleIndicator;
    private SMAIndicator smaIndicator;
    private ClosePriceIndicator closePriceIndicator;

    public TTMMomentumHistogram(final BarSeries series, final int donchianPeriod, final int smaPeriod) {
        super(series);
        this.donchianMiddleIndicator = new DonchianMiddleIndicator(series, donchianPeriod);
        this.closePriceIndicator = new ClosePriceIndicator(getBarSeries());
        this.smaIndicator = new SMAIndicator(closePriceIndicator, smaPeriod);
    }


    @Override
    protected Num calculate(int index) {
        return closePriceIndicator.getValue(index)
                .minus(

                        (donchianMiddleIndicator.getValue(index)
                                .plus(smaIndicator.getValue(index)))
                        .dividedBy(numOf(2))
                );
    }
}
