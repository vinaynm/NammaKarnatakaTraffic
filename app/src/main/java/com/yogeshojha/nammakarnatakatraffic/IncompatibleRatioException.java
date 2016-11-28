package com.yogeshojha.nammakarnatakatraffic;

/**
 * Created by y0g3sh on 28/11/16.
 */
public class IncompatibleRatioException extends RuntimeException {

    private static final long serialVersionUID = 234608108593115395L;

    public IncompatibleRatioException() {
        super("Can't perform Ken Burns effect on rects with distinct aspect ratios!");
    }
}