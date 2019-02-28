/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestLineUpdateComponent } from 'app/entities/credit-request-line/credit-request-line-update.component';
import { CreditRequestLineService } from 'app/entities/credit-request-line/credit-request-line.service';
import { CreditRequestLine } from 'app/shared/model/credit-request-line.model';

describe('Component Tests', () => {
    describe('CreditRequestLine Management Update Component', () => {
        let comp: CreditRequestLineUpdateComponent;
        let fixture: ComponentFixture<CreditRequestLineUpdateComponent>;
        let service: CreditRequestLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestLineUpdateComponent]
            })
                .overrideTemplate(CreditRequestLineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestLineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestLineService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequestLine(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequestLine = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CreditRequestLine();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.creditRequestLine = entity;
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
