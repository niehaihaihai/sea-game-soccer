package com.coocaa.ie.core.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.coocaa.ie.core.gdx.CCActor;
import com.coocaa.ie.core.gdx.CCGame;

public class TextureRegionActor extends CCActor {
    private TextureRegion region;

    public TextureRegionActor(CCGame game, Texture texture) {
        this(game, new TextureRegion(texture), false, false);
    }

    public TextureRegionActor(CCGame game, TextureRegion region) {
        this(game, region, false, false);
    }

    public TextureRegionActor(CCGame game, TextureRegion region, boolean flipX, boolean flipY) {
        super(game);
        if (region != null)
            region.flip(flipX, flipY);
        setRegion(region);
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        if (region != null)
            setSize(getGame().scale(this.region.getRegionWidth()), getGame().scale(this.region.getRegionHeight()));
        else
            setSize(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible()) {
            return;
        }

        /*
         * 先把 batch 原本的 color 保存起来, 因为 batch 是从外部传进来的, 最好不要改变它原本的状态,
         * 但在这里需要重新设置 batch 的 color, 所以先保存起来, 等当前方法执行完时再将 batch 原本的 color 设置回去。
         */
        Color tempBatchColor = batch.getColor();

        /*
         * 实际上演员并没有单独的 alpha 属性, alpha 包含在颜色(color)属性中, rgba color 中的 a 表示 alpha;
         * 演员有 alpha 值, 而父节点(舞台/演员组)中也有 alpha 值(parentAlpha)。 由于最终在演员节点中才真正把纹理
         * 绘制在屏幕上, 才是真正绘制的地方, 而父节点一般用于组织演员, 不会直接绘制任何纹理, 透明度 alpha 值只有在绘制
         * 时才能体现出来, 所以父节点无法体现自己的 alpha 值, 因此父节点会将自己的 alpha 值(就是draw方法中的参数 parentAlpha)
         * 传递给它自己的所有子节点，即最终直接绘制纹理的演员, 让演员结合自身的 alpha 值在绘制时综合体现。
         */

        // 获取演员的 color 属性
        Color color = getColor();

        /*
         * 处理 color/alpha 属性, 即将演员的 rgba color 设置到纹理画布 batch。
         * 其中的 alpha 需要结合演员和父节点的 alpha, 即演员的 alpha 与父节点的 alpha 相乘,
         * 例如父节点的 alpha 为 0.5, 演员的 alpha 为 0.5, 那么最终的显示效果就是 0.5 * 0.5 = 0.25
         */
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        /* 这里选择一个较为复杂的绘制方法进行绘制
        batch.draw(
                region,
                x, y,
                originX, originY,
                width, height,
                scaleX, scaleY,
                rotation
        );*/

        /*
         * 绘制纹理区域
         * 将演员中的 位置(position, 即 X, Y 坐标), 缩放和旋转支点(origin), 宽高尺寸, 缩放比, 旋转角度 应用到绘制中,
         * 最终 batch 会将综合结果绘制到屏幕上
         */
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
        // 将 batch 原本的 color 设置回去
        batch.setColor(tempBatchColor);
    }
}
