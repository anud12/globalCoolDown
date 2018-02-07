/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PawnListComponent } from './pawn-list.component';

describe('PawnListComponent', () => {
  let component: PawnListComponent;
  let fixture: ComponentFixture<PawnListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PawnListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PawnListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
