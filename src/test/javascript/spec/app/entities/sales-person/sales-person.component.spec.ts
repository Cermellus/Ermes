/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { SalesPersonComponent } from 'app/entities/sales-person/sales-person.component';
import { SalesPersonService } from 'app/entities/sales-person/sales-person.service';
import { SalesPerson } from 'app/shared/model/sales-person.model';

describe('Component Tests', () => {
    describe('SalesPerson Management Component', () => {
        let comp: SalesPersonComponent;
        let fixture: ComponentFixture<SalesPersonComponent>;
        let service: SalesPersonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [SalesPersonComponent],
                providers: []
            })
                .overrideTemplate(SalesPersonComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalesPersonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesPersonService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SalesPerson(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.salesPeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
