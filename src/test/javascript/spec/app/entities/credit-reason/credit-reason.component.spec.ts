/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { CreditReasonComponent } from 'app/entities/credit-reason/credit-reason.component';
import { CreditReasonService } from 'app/entities/credit-reason/credit-reason.service';
import { CreditReason } from 'app/shared/model/credit-reason.model';

describe('Component Tests', () => {
    describe('CreditReason Management Component', () => {
        let comp: CreditReasonComponent;
        let fixture: ComponentFixture<CreditReasonComponent>;
        let service: CreditReasonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReasonComponent],
                providers: []
            })
                .overrideTemplate(CreditReasonComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CreditReasonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditReasonService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CreditReason(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.creditReasons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
