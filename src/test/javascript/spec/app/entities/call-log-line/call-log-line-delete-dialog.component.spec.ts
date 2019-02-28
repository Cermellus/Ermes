/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CallLogLineDeleteDialogComponent } from 'app/entities/call-log-line/call-log-line-delete-dialog.component';
import { CallLogLineService } from 'app/entities/call-log-line/call-log-line.service';

describe('Component Tests', () => {
    describe('CallLogLine Management Delete Component', () => {
        let comp: CallLogLineDeleteDialogComponent;
        let fixture: ComponentFixture<CallLogLineDeleteDialogComponent>;
        let service: CallLogLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogLineDeleteDialogComponent]
            })
                .overrideTemplate(CallLogLineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CallLogLineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogLineService);
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
