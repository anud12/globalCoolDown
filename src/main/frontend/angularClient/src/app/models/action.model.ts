export abstract class Action {

  constructor(public id: number,
              public name: String,
              public targetId: String,
              public duration: number) {
  }
}
