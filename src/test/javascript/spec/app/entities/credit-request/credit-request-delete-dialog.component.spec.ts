/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestDeleteDialogComponent } from 'app/entities/credit-request/credit-request-delete-dialog.component';
import { CreditRequestService } from 'app/entities/credit-request/credit-request.service';

describe('Component Tests', () => {
    describe('CreditRequest Management Delete Component', () => {
        let comp: CreditRequestDeleteDialogComponent;
        let fixture: ComponentFixture<CreditRequestDeleteDialogComponent>;
        let service: CreditRequestService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestDeleteDialogComponent]
            })
                .overrideTemplate(CreditRequestDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditRequestDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestService);
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
