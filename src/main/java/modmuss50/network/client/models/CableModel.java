package modmuss50.network.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created with IntelliJ IDEA. User: Mark Date: 24/02/14 Time: 13:11
 */
public class CableModel extends ModelBase {
    // fields
    public ModelRenderer Main;
    public ModelRenderer Up;
    public ModelRenderer Down;
    public ModelRenderer Left;
    public ModelRenderer Right;
    public ModelRenderer Back;
    public ModelRenderer Forward;

    public CableModel() {
        textureWidth = 128;
        textureHeight = 128;

        Main = new ModelRenderer(this, 0, 0);
        Main.addBox(0F, 0F, 0F, 5, 5, 5);
        Main.setRotationPoint(-2F, 13F, -3F);
        Main.setTextureSize(128, 128);
        Main.mirror = true;
        setRotation(Main, 0F, 0F, 0F);
        Up = new ModelRenderer(this, 23, 0);
        Up.addBox(0F, 0F, 0F, 3, 6, 3);
        Up.setRotationPoint(-1F, 7.5F, -2F);
        Up.setTextureSize(128, 128);
        Up.mirror = true;
        setRotation(Up, 0F, 0F, 0F);
        Down = new ModelRenderer(this, 23, 0);
        Down.addBox(0F, 0F, 0F, 3, 6, 3);
        Down.setRotationPoint(-1F, 17.5F, -2F);
        Down.setTextureSize(128, 128);
        Down.mirror = true;
        setRotation(Down, 0F, 0F, 0F);
        Left = new ModelRenderer(this, 23, 11);
        Left.addBox(0F, 0F, 0F, 6, 3, 3);
        Left.setRotationPoint(-7.5F, 14F, -2F);
        Left.setTextureSize(128, 128);
        Left.mirror = true;
        setRotation(Left, 0F, 0F, 0F);
        Right = new ModelRenderer(this, 23, 11);
        Right.addBox(0F, 0F, 0F, 6, 3, 3);
        Right.setRotationPoint(2.5F, 14F, -2F);
        Right.setTextureSize(128, 128);
        Right.mirror = true;
        setRotation(Right, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 23, 20);
        Back.addBox(0F, 0F, 0F, 3, 3, 6);
        Back.setRotationPoint(-1F, 14F, 1.5F);
        Back.setTextureSize(128, 128);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Forward = new ModelRenderer(this, 23, 20);
        Forward.addBox(0F, 0F, 0F, 3, 3, 6);
        Forward.setRotationPoint(-1F, 14F, -8.5F);
        Forward.setTextureSize(128, 128);
        Forward.mirror = true;
        setRotation(Forward, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        Main.render(f5);
        Up.render(f5);
        Down.render(f5);
        Left.render(f5);
        Right.render(f5);
        Back.render(f5);
        Forward.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }
}
