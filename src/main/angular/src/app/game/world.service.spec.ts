import { TestBed } from '@angular/core/testing';

import { WorldService } from './world.service';

describe('WorldService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WorldService = TestBed.get(WorldService);
    expect(service).toBeTruthy();
  });
});
