import { TestBed } from '@angular/core/testing';

import { GameObjectService } from './game-object.service';

describe('GameObjectService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GameObjectService = TestBed.get(GameObjectService);
    expect(service).toBeTruthy();
  });
});
