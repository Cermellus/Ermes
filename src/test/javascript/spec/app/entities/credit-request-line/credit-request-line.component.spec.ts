/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestLineComponent } from 'app/entities/credit-request-line/credit-request-line.component';
import { CreditRequestLineService } from 'app/entities/credit-request-line/credit-request-line.service';
import { CreditRequestLine } from 'app/shared/model/credit-request-line.model';

describe('Component Tests', () => {
    describe('CreditRequestLine Management Component', () => {
        let comp: CreditRequestLineComponent;
        let fixture: ComponentFixture<CreditRequestLineComponent>;
        let service: CreditRequestLineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestLineComponent],
                providers: []
            })
                .overrideTemplate(CreditRequestLineComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditRequestLineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditRequestLineService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditRequestLine(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditRequestLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
