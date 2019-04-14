import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameObjectViewComponent } from './game-object-view.component';

describe('GameObjectViewComponent', () => {
  let component: GameObjectViewComponent;
  let fixture: ComponentFixture<GameObjectViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameObjectViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameObjectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
