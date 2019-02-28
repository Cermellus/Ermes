/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestLineDeleteDialogComponent } from 'app/entities/credit-request-line/credit-request-line-delete-dialog.component';
import { CreditRequestLineService } from 'app/entities/credit-request-line/credit-request-line.service';

describe('Component Tests', () => {
    describe('CreditRequestLine Management Delete Component', () => {
        let comp: CreditRequestLineDeleteDialogComponent;
        let fixture: ComponentFixture<CreditRequestLineDeleteDialogComponent>;
        let service: CreditRequestLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestLineDeleteDialogComponent]
            })
                .overrideTemplate(CreditRequestLineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditRequestLineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestLineService);
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
