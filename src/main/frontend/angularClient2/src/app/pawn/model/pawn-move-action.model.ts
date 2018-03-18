export class PawnMoveActionModel {

  public type: string = "MOVE_ACTION";

  constructor(public pawnId: number,
              public x: number,
              public y: number) {
  }
}
