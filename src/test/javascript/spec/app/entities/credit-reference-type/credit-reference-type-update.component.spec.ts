/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReferenceTypeUpdateComponent } from 'app/entities/credit-reference-type/credit-reference-type-update.component';
import { CreditReferenceTypeService } from 'app/entities/credit-reference-type/credit-reference-type.service';
import { CreditReferenceType } from 'app/shared/model/credit-reference-type.model';

describe('Component Tests', () => {
    describe('CreditReferenceType Management Update Component', () => {
        let comp: CreditReferenceTypeUpdateComponent;
        let fixture: ComponentFixture<CreditReferenceTypeUpdateComponent>;
        let service: CreditReferenceTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReferenceTypeUpdateComponent]
            })
                .overrideTemplate(CreditReferenceTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReferenceTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReferenceTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReferenceType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReferenceType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReferenceType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReferenceType = entity;
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
