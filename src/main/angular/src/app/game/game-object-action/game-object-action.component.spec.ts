import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameObjectActionComponent } from './game-object-action.component';

describe('GameObjectActionComponent', () => {
  let component: GameObjectActionComponent;
  let fixture: ComponentFixture<GameObjectActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameObjectActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameObjectActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
