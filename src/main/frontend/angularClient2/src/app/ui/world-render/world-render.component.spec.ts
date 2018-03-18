import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorldRenderComponent } from './world-render.component';

describe('WorldRenderComponent', () => {
  let component: WorldRenderComponent;
  let fixture: ComponentFixture<WorldRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorldRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorldRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
