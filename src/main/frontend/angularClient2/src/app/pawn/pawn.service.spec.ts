import { TestBed, inject } from '@angular/core/testing';

import { PawnService } from './pawn.service';

describe('PawnService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PawnService]
    });
  });

  it('should be created', inject([PawnService], (service: PawnService) => {
    expect(service).toBeTruthy();
  }));
});
