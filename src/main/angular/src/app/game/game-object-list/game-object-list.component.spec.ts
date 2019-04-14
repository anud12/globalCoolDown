import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameObjectListComponent } from './game-object-list.component';

describe('GameObjectListComponent', () => {
  let component: GameObjectListComponent;
  let fixture: ComponentFixture<GameObjectListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameObjectListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameObjectListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
