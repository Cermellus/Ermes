/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestStatusUpdateComponent } from 'app/entities/credit-request-status/credit-request-status-update.component';
import { CreditRequestStatusService } from 'app/entities/credit-request-status/credit-request-status.service';
import { CreditRequestStatus } from 'app/shared/model/credit-request-status.model';

describe('Component Tests', () => {
    describe('CreditRequestStatus Management Update Component', () => {
        let comp: CreditRequestStatusUpdateComponent;
        let fixture: ComponentFixture<CreditRequestStatusUpdateComponent>;
        let service: CreditRequestStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestStatusUpdateComponent]
            })
                .overrideTemplate(CreditRequestStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequestStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequestStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequestStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequestStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
