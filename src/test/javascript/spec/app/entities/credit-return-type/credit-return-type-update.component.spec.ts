/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReturnTypeUpdateComponent } from 'app/entities/credit-return-type/credit-return-type-update.component';
import { CreditReturnTypeService } from 'app/entities/credit-return-type/credit-return-type.service';
import { CreditReturnType } from 'app/shared/model/credit-return-type.model';

describe('Component Tests', () => {
    describe('CreditReturnType Management Update Component', () => {
        let comp: CreditReturnTypeUpdateComponent;
        let fixture: ComponentFixture<CreditReturnTypeUpdateComponent>;
        let service: CreditReturnTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReturnTypeUpdateComponent]
            })
                .overrideTemplate(CreditReturnTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReturnTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReturnTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReturnType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReturnType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditReturnType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditReturnType = entity;
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
