import { TestBed } from '@angular/core/testing';

import { StompService } from './stomp.service';

describe('StompService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StompService = TestBed.get(StompService);
    expect(service).toBeTruthy();
  });
});
