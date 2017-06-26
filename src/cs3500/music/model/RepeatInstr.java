package cs3500.music.model;

/**
 * Represents a set of instructions to repeat a certain part of a music model. The first and last
 * beats here are inclusive.
 */
public class RepeatInstr {
  private int lastBeat;
  private int firstBeat;
  private boolean completed;

  public RepeatInstr(int firstBeat, int lastbeat) {
    if (firstBeat < 0 || lastbeat < 0) {
      throw new IllegalArgumentException("Inputs must be positive.");
    }
    if (lastbeat < firstBeat) {
      throw new IllegalArgumentException("Last beat must be >= first beat.");
    }
    this.firstBeat = firstBeat;
    this.lastBeat = lastbeat;
    this.completed = false;
  }

  public boolean isCompleted() {
    return completed;
  }

  public int firstBeat() {
    return firstBeat;
  }

  public int lastBeat() {
    return lastBeat;
  }

  public void complete() {
    completed = true;
  }

  public void uncomplete() {
    completed = false;
  }

  public RepeatInstr copy() {
    RepeatInstr r = new RepeatInstr(this.firstBeat, this.lastBeat);
    if (this.completed) {
      r.complete();
    }
    return r;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof RepeatInstr) {
      RepeatInstr oRep = (RepeatInstr) o;
      return oRep.isCompleted() == this.isCompleted() && oRep.firstBeat() == this.firstBeat()
          && oRep.lastBeat() == this.lastBeat();
    }
    return false;
  }

  @Override
  public int hashCode() {
    int boolInt = completed ? 1 : 0;
    return this.firstBeat * 1000000 + this.lastBeat() * 10 + boolInt;
  }

}
