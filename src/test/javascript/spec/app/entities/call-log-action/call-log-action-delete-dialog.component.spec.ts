/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CallLogActionDeleteDialogComponent } from 'app/entities/call-log-action/call-log-action-delete-dialog.component';
import { CallLogActionService } from 'app/entities/call-log-action/call-log-action.service';

describe('Component Tests', () => {
    describe('CallLogAction Management Delete Component', () => {
        let comp: CallLogActionDeleteDialogComponent;
        let fixture: ComponentFixture<CallLogActionDeleteDialogComponent>;
        let service: CallLogActionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogActionDeleteDialogComponent]
            })
                .overrideTemplate(CallLogActionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CallLogActionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogActionService);
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
