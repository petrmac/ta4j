package org.ta4j.core.indicators.ttmsqueeze;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicatorTest;
import org.ta4j.core.num.Num;

import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TTMDotIndicatorTest extends AbstractIndicatorTest<Indicator<Num>, Num> {
    private BarSeries data;
    public TTMDotIndicatorTest(Function<Number, Num> numFunction) {
        super(null, numFunction);
    }

    @Before
    public void init() {
        data = new BaseBarSeriesBuilder().withNumTypeOf(numFunction)
                .withName("TTMDot data").build();
        BtcTestData.fillTestData(data);
    }

    @Test
    public void upDownAndHigh() {
        TTMDotIndicator ttmDotIndicator = new TTMDotIndicator(data, 20, 1.5d, 10);
        assertTrue(ttmDotIndicator.getValue(20));
        assertTrue(ttmDotIndicator.getValue(21));
        assertTrue(ttmDotIndicator.getValue(22));
        assertTrue(ttmDotIndicator.getValue(23));
        assertFalse(ttmDotIndicator.getValue(24));
        assertFalse(ttmDotIndicator.getValue(25));
        assertTrue(ttmDotIndicator.getValue(26));
        assertTrue(ttmDotIndicator.getValue(27));
        assertTrue(ttmDotIndicator.getValue(28));
        assertTrue(ttmDotIndicator.getValue(29));
        assertTrue(ttmDotIndicator.getValue(30));

    }
}