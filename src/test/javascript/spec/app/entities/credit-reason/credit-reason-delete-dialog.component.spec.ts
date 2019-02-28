/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CreditReasonDeleteDialogComponent } from 'app/entities/credit-reason/credit-reason-delete-dialog.component';
import { CreditReasonService } from 'app/entities/credit-reason/credit-reason.service';

describe('Component Tests', () => {
    describe('CreditReason Management Delete Component', () => {
        let comp: CreditReasonDeleteDialogComponent;
        let fixture: ComponentFixture<CreditReasonDeleteDialogComponent>;
        let service: CreditReasonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReasonDeleteDialogComponent]
            })
                .overrideTemplate(CreditReasonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReasonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReasonService);
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
