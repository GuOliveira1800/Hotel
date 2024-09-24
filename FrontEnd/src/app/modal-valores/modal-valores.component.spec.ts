import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalValoresComponent } from './modal-valores.component';

describe('ModalValoresComponent', () => {
  let component: ModalValoresComponent;
  let fixture: ComponentFixture<ModalValoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalValoresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalValoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
