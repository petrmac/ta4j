package org.ta4j.core.indicators.donchian;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.num.Num;

public class DonchianMiddleIndicator extends CachedIndicator<Num> {

    private DonchianLowerIndicator donchianLowerIndicator;
    private DonchianUpperIndicator donchianUpperIndicator;

    public DonchianMiddleIndicator(final DonchianLowerIndicator donchianLowerIndicator,
                                   final DonchianUpperIndicator donchianUpperIndicator) {
        super(donchianLowerIndicator.getBarSeries());
        this.donchianLowerIndicator = donchianLowerIndicator;
        this.donchianUpperIndicator = donchianUpperIndicator;
    }

    public DonchianMiddleIndicator(final BarSeries series, final int donchianPeriod) {
        super(series);
        this.donchianLowerIndicator = new DonchianLowerIndicator(series, donchianPeriod);
        this.donchianUpperIndicator = new DonchianUpperIndicator(series, donchianPeriod);
    }

    @Override
    protected Num calculate(int index) {
        return (donchianUpperIndicator.getValue(index)
                .plus(donchianLowerIndicator.getValue(index))).dividedBy(numOf(2));
    }
}
