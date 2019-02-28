/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditReturnTypeComponent } from 'app/entities/credit-return-type/credit-return-type.component';
import { CreditReturnTypeService } from 'app/entities/credit-return-type/credit-return-type.service';
import { CreditReturnType } from 'app/shared/model/credit-return-type.model';

describe('Component Tests', () => {
    describe('CreditReturnType Management Component', () => {
        let comp: CreditReturnTypeComponent;
        let fixture: ComponentFixture<CreditReturnTypeComponent>;
        let service: CreditReturnTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReturnTypeComponent],
                providers: []
            })
                .overrideTemplate(CreditReturnTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReturnTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReturnTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditReturnType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditReturnTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
