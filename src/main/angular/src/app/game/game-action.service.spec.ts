import { TestBed } from '@angular/core/testing';

import { GameCommandService } from './game-command.service';

describe('GameCommandService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GameCommandService = TestBed.get(GameCommandService);
    expect(service).toBeTruthy();
  });
});
