package org.ta4j.core.indicators.donchian;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.num.Num;

public class DonchianUpperIndicator extends CachedIndicator<Num> {
    private final HighestValueIndicator highestValueIndicator;

    private final int barCount;

    public DonchianUpperIndicator(Indicator<Num> indicator, int barCount) {
        super(indicator);
        this.barCount = barCount;
        highestValueIndicator = new HighestValueIndicator(indicator, barCount);
    }

    public DonchianUpperIndicator(final BarSeries series, final int barCount) {
        super(series);
        this.barCount = barCount;
        highestValueIndicator = new HighestValueIndicator(new HighPriceIndicator(series), this.barCount);
    }

    @Override
    protected Num calculate(int index) {
        return highestValueIndicator.getValue(index);
    }
}
