/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CreditReferenceTypeDeleteDialogComponent } from 'app/entities/credit-reference-type/credit-reference-type-delete-dialog.component';
import { CreditReferenceTypeService } from 'app/entities/credit-reference-type/credit-reference-type.service';

describe('Component Tests', () => {
    describe('CreditReferenceType Management Delete Component', () => {
        let comp: CreditReferenceTypeDeleteDialogComponent;
        let fixture: ComponentFixture<CreditReferenceTypeDeleteDialogComponent>;
        let service: CreditReferenceTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReferenceTypeDeleteDialogComponent]
            })
                .overrideTemplate(CreditReferenceTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReferenceTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReferenceTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
