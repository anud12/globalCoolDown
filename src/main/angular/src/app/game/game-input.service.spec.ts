import { TestBed } from '@angular/core/testing';

import { GameInputService } from './game-input.service';

describe('GameInputService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GameInputService = TestBed.get(GameInputService);
    expect(service).toBeTruthy();
  });
});
