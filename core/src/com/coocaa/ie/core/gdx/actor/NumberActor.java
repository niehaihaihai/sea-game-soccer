package com.coocaa.ie.core.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCGame;
import com.coocaa.ie.core.gdx.CCGroup;

import java.util.Stack;

public class NumberActor extends CCGroup {
    private TextureRegion[] textureRegions;
    private Stack<TextureRegionActor> numbers = new Stack<TextureRegionActor>();
    private float offset;
    private int value;

    public NumberActor(CCGame game, TextureRegion[] textureRegions, float offset) {
        super(game);
        this.offset = offset;
        this.textureRegions = textureRegions;
        setNumber(0);
    }

    public int getNumber() {
        synchronized (numbers) {
            return value;
        }
    }

    public void setNumber(final int value) {
        clearChildren();
        synchronized (numbers) {
//            Gdx.app.log("num", "setNumber:" + value);
            this.value = value;
            numbers.clear();
            int _value = value;
            if (_value >= 10)
                while (_value > 0) {
                    int nn = _value % 10;
                    numbers.push(new TextureRegionActor(getGame(), textureRegions[nn]));
                    _value /= 10;
                }
            else {
                numbers.push(new TextureRegionActor(getGame(), textureRegions[_value]));
            }
            int size = numbers.size();
            float _width = 0;
            float _height = 0;
            int i = 0;
            while (true) {
                TextureRegionActor number = numbers.pop();
                if (number.getHeight() > _height)
                    _height = number.getHeight();
                addActor(number);
                float x = 0;
                if (i != 0) {
                    x += offset * i + _width;
                }
                float _x = x;
                float _y = 0;
//                Gdx.app.log("num", "x:" + _x + "   y:" + _y);
                number.setPosition(_x, _y);
                _width += number.getWidth();
                if (numbers.empty())
                    break;
                i++;
            }
            _width += (size - 1) * offset;
//            Gdx.app.log("num", "_width:" + _width + "   _height:" + _height);
            setSize(_width, _height);
        }
    }
}
