package org.ta4j.core.indicators.ttmsqueeze;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicatorTest;
import org.ta4j.core.num.Num;

import java.util.function.Function;

import static org.ta4j.core.TestUtils.assertNumEquals;

public class TTMMomentumHistogramTest extends AbstractIndicatorTest<Indicator<Num>, Num> {
    private BarSeries data;

    public TTMMomentumHistogramTest(Function<Number, Num> numFunction) {
        super(null, numFunction);
    }

    @Before
    public void init() {
        data = new BaseBarSeriesBuilder().withNumTypeOf(numFunction)
                .withName("TTMDot data")
                .build();
        BtcTestData.fillTestData(data);
    }

    @Test
    public void upDownAndHigh() {
        TTMMomentumHistogram ttmMomentumHistogram = new TTMMomentumHistogram(data, 20, 20);
        assertNumEquals(57.0525, ttmMomentumHistogram.getValue(20));
        assertNumEquals(-154.52, ttmMomentumHistogram.getValue(21));
        assertNumEquals(-133.3925, ttmMomentumHistogram.getValue(22));

        assertNumEquals(-176.475, ttmMomentumHistogram.getValue(30));
    }
}