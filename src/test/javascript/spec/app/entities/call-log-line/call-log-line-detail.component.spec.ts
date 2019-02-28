/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogLineDetailComponent } from 'app/entities/call-log-line/call-log-line-detail.component';
import { CallLogLine } from 'app/shared/model/call-log-line.model';

describe('Component Tests', () => {
    describe('CallLogLine Management Detail Component', () => {
        let comp: CallLogLineDetailComponent;
        let fixture: ComponentFixture<CallLogLineDetailComponent>;
        const route = ({ data: of({ callLogLine: new CallLogLine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogLineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CallLogLineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CallLogLineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.callLogLine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
