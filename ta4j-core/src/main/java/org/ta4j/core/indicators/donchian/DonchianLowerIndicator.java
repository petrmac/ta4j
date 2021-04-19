package org.ta4j.core.indicators.donchian;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.num.Num;

public class DonchianLowerIndicator extends CachedIndicator<Num> {
    private final LowestValueIndicator lowestValueIndicator;

    private final int barCount;

    public DonchianLowerIndicator(Indicator<Num> indicator, int barCount) {
        super(indicator);
        this.barCount = barCount;
        lowestValueIndicator = new LowestValueIndicator(indicator, barCount);
    }

    public DonchianLowerIndicator(final BarSeries series, final int barCount) {
        super(series);
        this.barCount = barCount;
        lowestValueIndicator = new LowestValueIndicator(new LowPriceIndicator(series), this.barCount);
    }

    @Override
    protected Num calculate(int index) {
        return lowestValueIndicator.getValue(index);
    }
}
