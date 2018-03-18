import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InteractiveRenderComponent } from './interactive-render.component';

describe('InteractiveRenderComponent', () => {
  let component: InteractiveRenderComponent;
  let fixture: ComponentFixture<InteractiveRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InteractiveRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InteractiveRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
