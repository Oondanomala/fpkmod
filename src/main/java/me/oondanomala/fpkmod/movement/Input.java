package me.oondanomala.fpkmod.movement;

public class Input {
    public float forwardMove = 0.0f;
    public float strafeMove = 0.0f;
    public boolean sprinting = true;
    public boolean jumping = false;
    public boolean sneaking = false;
    public int duration = 1;
    public Input(){}
    public void walk() {
        this.sprinting = false;
    }
    public void jump() {
        this.jumping = true;
    }
    public void sneak() {
        this.sneaking = true;
    }
    public void move(float forwards, float sideways) {
        this.forwardMove = forwards;
        this.strafeMove = sideways;
    }
    public boolean isMoving() {
        return forwardMove > 0.0f || (strafeMove > 0.0f);
    }
    public boolean isEqual(Input other) {
        return forwardMove == other.forwardMove && strafeMove == other.strafeMove && sprinting == other.sprinting && jumping == other.jumping && sneaking == other.sneaking;
    }

}
