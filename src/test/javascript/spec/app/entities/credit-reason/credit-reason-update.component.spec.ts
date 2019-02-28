/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReasonUpdateComponent } from 'app/entities/credit-reason/credit-reason-update.component';
import { CreditReasonService } from 'app/entities/credit-reason/credit-reason.service';
import { CreditReason } from 'app/shared/model/credit-reason.model';

describe('Component Tests', () => {
    describe('CreditReason Management Update Component', () => {
        let comp: CreditReasonUpdateComponent;
        let fixture: ComponentFixture<CreditReasonUpdateComponent>;
        let service: CreditReasonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReasonUpdateComponent]
            })
                .overrideTemplate(CreditReasonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReasonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReasonService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReason(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReason = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReason();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReason = entity;
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
