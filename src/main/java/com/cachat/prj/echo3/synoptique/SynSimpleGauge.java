package com.cachat.prj.echo3.synoptique;

/**
 *
 * @author scachat
 */
public class SynSimpleGauge extends SynGroupe {

    /**
     * widget background
     */
    private SynObject back;
    /**
     * widget jauge
     */
    private SynObject gauge;
    /**
     * widget texte
     */
    private SynText text;
    /**
     * valeur actuelle
     */
    private double value;
    /**
     * *
     * valeur minimum
     */
    private double min;
    /**
     * valeur maximum
     */
    private double max;
    /**
     * hauteur de la jauge
     */
    private int height;
    /**
     * largeur des bordures
     */
    private int border;
    /**
     * position de la jauge
     */
    private int top;
    /**
     * position de l'indicateur
     */
    private double topGauge;
    /**
     * hauteur de l'indicateur
     */
    private double heightGauge;
    /**
     * le texte formaté
     */
    private String label;
    /**
     * le format pour le label
     */
    private String format = "%1.1f%%\n%1.0f";

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public SynSimpleGauge(int left, int top, int width, int height, int border, String color, double value, double min, double max) {
        this.min = min;
        this.max = max;
        this.top = top;
        this.height = height;
        this.border = border;
        this.value = value;
        calcHeight();
        back = new SynShape(SynShapeType.RECT, "0x000000", "0x000000", left, top, width, height);
        gauge = new SynShape(SynShapeType.RECT, color, color, left + border, topGauge, width - border * 2, heightGauge);

        text = new SynText(border * 2, top + height / 2, width, height, label, 20);
//        text.setFill(0xff0000);
//        text.setStroke(0x00ff00);
//        text.setTextBackgroundColor(0x000000);

        add(back);
        add(gauge);
        add(text);

    }

    public void setValue(double value) {
        this.value = value;
        calcHeight();
        gauge.setTop(topGauge);
        gauge.setHeight(heightGauge);
        text.setText(label);
        Synoptique synoptique = gauge.getSynoptique();
        if (synoptique != null) {
            synoptique.update(gauge);
            synoptique.update(text);
        }
    }

    private void calcHeight() {
        double y = (max == min) ? 0 : ((value - min) / (max - min));
        heightGauge = height * y - border * 2;
        topGauge = top + border + height - heightGauge;
        label = String.format(format, y * 100, value);
    }

}
