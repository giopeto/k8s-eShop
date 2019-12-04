import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsInGroupComponent } from './items-in-group.component';

describe('ItemsInGroupComponent', () => {
  let component: ItemsInGroupComponent;
  let fixture: ComponentFixture<ItemsInGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemsInGroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsInGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
