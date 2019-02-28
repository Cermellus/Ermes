/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ErmesTestModule } from '../../../test.module';
import { CreditReturnTypeDeleteDialogComponent } from 'app/entities/credit-return-type/credit-return-type-delete-dialog.component';
import { CreditReturnTypeService } from 'app/entities/credit-return-type/credit-return-type.service';

describe('Component Tests', () => {
    describe('CreditReturnType Management Delete Component', () => {
        let comp: CreditReturnTypeDeleteDialogComponent;
        let fixture: ComponentFixture<CreditReturnTypeDeleteDialogComponent>;
        let service: CreditReturnTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReturnTypeDeleteDialogComponent]
            })
                .overrideTemplate(CreditReturnTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReturnTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReturnTypeService);
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
