/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditReferenceTypeComponent } from 'app/entities/credit-reference-type/credit-reference-type.component';
import { CreditReferenceTypeService } from 'app/entities/credit-reference-type/credit-reference-type.service';
import { CreditReferenceType } from 'app/shared/model/credit-reference-type.model';

describe('Component Tests', () => {
    describe('CreditReferenceType Management Component', () => {
        let comp: CreditReferenceTypeComponent;
        let fixture: ComponentFixture<CreditReferenceTypeComponent>;
        let service: CreditReferenceTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReferenceTypeComponent],
                providers: []
            })
                .overrideTemplate(CreditReferenceTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReferenceTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReferenceTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditReferenceType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditReferenceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
