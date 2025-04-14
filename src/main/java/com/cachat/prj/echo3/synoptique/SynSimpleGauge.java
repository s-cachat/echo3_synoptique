package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynSimpleGauge extends SynGroupe {

    private SynObject back;
    private SynObject gauge;
    private SynText text;
    private double value;
    private double min;
    private double max;
    private int height;
    private int border;
    private int top;
    private double topGauge;
    private double heightGauge;
    private String label;

    public SynSimpleGauge(int left, int top, int width, int height, int border, int color, double value, double min, double max) {
        this.min = min;
        this.max = max;
        this.top = top;
        this.height = height;
        this.border = border;
        this.value = value;
        calcHeight();
        back = new SynObject(left, top, width, height, new SynViewBasic(SynViewBasic.SubType.RECT, 0x000000));
        gauge = new SynObject(left + border, topGauge, width - border * 2, heightGauge, new SynViewBasic(SynViewBasic.SubType.RECT, 0x000000));

        text = new SynText(left + width / 2, top + height / 2, width, height, label, 20);
        text.setFill(0xBBFFBB);
        text.setStroke(0xBBFFFF);
        text.setTextBackgroundColor(0xffFFbb);

        add(back);
        add(gauge);
        add(text);

    }

    public void setValue(double value) {
        this.value = value;
        Synoptique synoptique = gauge.getSynoptique();
        if (synoptique != null) {
            synoptique.update(gauge);
        }
    }

    private void calcHeight() {
        double y = (max == min) ? 0 : ((value - min) / (max - min));
        heightGauge = height * y;
        topGauge = top + border + height - heightGauge;
        label = String.format("%1.1f%%\n%1.0f",y,value);
    }

}
