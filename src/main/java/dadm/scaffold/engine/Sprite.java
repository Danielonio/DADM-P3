package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import 	android.graphics.Rect;
import android.graphics.Paint;

import java.util.List;

import dadm.scaffold.R;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation;

    protected double pixelFactor;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected final int imageWidth;
    public Rect rect;
    Paint paint;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes,int color_nave) {//-1 balas y enemigos 0 Nave1 0 nave2
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable;
        if(color_nave==0){
            spriteDrawable = r.getDrawable(R.drawable.ship0);}
        else if(color_nave==1){
            spriteDrawable = r.getDrawable(R.drawable.ship1);}
            else{
            spriteDrawable = r.getDrawable(drawableRes);}



        this.pixelFactor = gameEngine.pixelFactor;

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(3);
        rect = new Rect((int)positionX,(int)positionY,(int)positionX+imageWidth, (int)positionY+imageHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - imageWidth
                || positionY < - imageHeight) {
            return;
        }
        rect = new Rect((int)positionX,(int)positionY,(int)positionX+imageWidth, (int)positionY+imageHeight);
        //canvas.drawRect((float)positionX,(float)positionY,(float)positionX+imageWidth, (float)positionY+imageHeight,paint);
        canvas.drawRect(rect,paint);
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + imageWidth/2), (float) (positionY + imageHeight/2));
        canvas.drawBitmap(bitmap, matrix, null);
    }
    /*
    public Rect getRect(){
        return rect;
    }*/

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> resto, int i);

    public Rect getRect() {
        return rect;
    }
}
