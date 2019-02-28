/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestUpdateComponent } from 'app/entities/credit-request/credit-request-update.component';
import { CreditRequestService } from 'app/entities/credit-request/credit-request.service';
import { CreditRequest } from 'app/shared/model/credit-request.model';

describe('Component Tests', () => {
    describe('CreditRequest Management Update Component', () => {
        let comp: CreditRequestUpdateComponent;
        let fixture: ComponentFixture<CreditRequestUpdateComponent>;
        let service: CreditRequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestUpdateComponent]
            })
                .overrideTemplate(CreditRequestUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequest(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequest = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequest();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequest = entity;
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
